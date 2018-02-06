/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { Component, EventEmitter, HostListener, Input, Output, ViewChild } from '@angular/core';

import * as moment from 'moment';
import * as d3 from 'd3';

@Component({
    selector: 'wikift-contribution',
    templateUrl: './wikift-contribution.component.html',
    styles: [`
    :host {
      position: relative;
      user-select: none;
      -ms-user-select: none;
      -moz-user-select: none;
      -webkit-user-select: none;
    }
    :host >>> .item {
      cursor: pointer;
    }
    :host >>> .label {
      cursor: pointer;
      fill: rgb(170, 170, 170);
    }
    :host >>> .heatmap-tooltip {
      pointer-events: none;
      position: absolute;
      z-index: 9999;
      overflow: hidden;
      padding: 0.3rem;
      font-size: 12px;
      line-height: 14px;
      color: rgb(51, 51, 51);
      background: #495057;
      white-space: nowrap;
      overflow: hidden;
      color: #ffffff;
      padding: 1rem;
      border-radius: 0.3rem;
      text-overflow: ellipsis;
      display: inline-block;
    }
  `]
})
export class WikiftContributionComponent {

    // 图表容器
    @ViewChild('wikiftContribution')
    element: any;

    // 渲染数据
    @Input()
    data: Array<object>;

    // 颜色
    @Input()
    color = '#7bc96f';

    // 时区
    @Input()
    locale = 'zh-cn';

    @Output()
    handler: EventEmitter<object> = new EventEmitter<object>();

    @Output()
    onChange: EventEmitter<object> = new EventEmitter<object>();

    // 默认配置
    private gutter = 5;
    private item_gutter = 1;
    private width = 1000;
    private height = 200;
    private item_size = 10;
    private label_padding = 40;
    private max_block_height = 20;
    private transition_duration = 500;
    private in_transition = false;

    // tooltip 配置
    private tooltip_width = 250;
    private tooltip_padding = 15;

    private selected = {};

    // D3
    private svg: any;
    private items: any;
    private labels: any;
    private buttons: any;
    private tooltip: any;

    ngOnChanges() {
        if (!this.data) {
            return;
        }
        this.drawChart();
    }

    ngAfterViewInit() {
        // 设置为中国时区
        const element = this.element.nativeElement;
        // 初始化 svg
        this.svg = d3.select(element)
            .append('svg')
            .attr('class', 'svg');
        // 初始化 svg 全局容器
        this.items = this.svg.append('g');
        this.labels = this.svg.append('g');
        this.buttons = this.svg.append('g');
        // 添加 tooltip
        this.tooltip = d3.select(element).append('div')
            .attr('class', 'heatmap-tooltip')
            .style('opacity', 0);
        this.calculateDimensions();
        this.drawChart();
    }

    getNumberOfWeeks() {
        const dayIndex = Math.round((+moment() - +moment().subtract(1, 'year').startOf('week')) / 86400000);
        const colIndex = Math.trunc(dayIndex / 7);
        const numWeeks = colIndex + 1;
        return numWeeks;
    }

    /**
     * 计算图尺寸
     */
    calculateDimensions() {
        const element = this.element.nativeElement;
        this.width = element.clientWidth < 1000 ? 1000 : element.clientWidth;
        this.item_size = ((this.width - this.label_padding) / this.getNumberOfWeeks() - this.gutter);
        this.height = this.label_padding + 7 * (this.item_size + this.gutter);
        this.svg.attr('width', this.width).attr('height', this.height);
    }

    /**
     * 绘制图表
     */
    drawChart() {
        if (!this.svg || !this.data) {
            return;
        }
        this.drawYearOverview();
        this.onChange.emit({
            start: moment(this.selected['date']).startOf('year'),
            end: moment(this.selected['date']).endOf('year'),
        });
    }

    /**
     * 根据年份进行绘制图表
     */
    drawYearOverview() {
        moment.locale(this.locale);
        // 定义开始/结束时间
        const start_of_year = moment(this.selected['date']).startOf('year');
        const end_of_year = moment(this.selected['date']).endOf('year');
        // 填充年度数据
        const year_data = this.data.filter((d: any) => {
            return start_of_year <= moment(d.date) && moment(d.date) < end_of_year;
        });
        // 计算当前年份数据最大值
        const max_value = d3.max(year_data, (d: any) => {
            return d.total;
        });
        const color = d3.scaleLinear<string>()
            .range(['#ffffff', this.color])
            .domain([-0.15 * max_value, max_value]);
        this.items.selectAll('.item-circle').remove();
        this.items.selectAll('.item-circle')
            .data(year_data)
            .enter()
            .append('rect')
            .attr('class', 'item item-circle')
            .style('opacity', 0)
            .attr('x', (d: any) => {
                return this.calcItemX(d, start_of_year) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
            })
            .attr('y', (d: any) => {
                return this.calcItemY(d) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
            })
            .attr('width', (d: any) => {
                return this.calcItemSize(d, max_value);
            })
            .attr('height', (d: any) => {
                return this.calcItemSize(d, max_value);
            })
            .attr('fill', (d: any) => {
                return (d.total > 0) ? color(d.total) : '#ebedf0';
            })
            .on('mouseover', (d: any) => {
                if (this.in_transition) {
                    return;
                }
                const circle = d3.select(d3.event.currentTarget);
                const repeat = () => {
                    circle.transition()
                        .duration(this.transition_duration)
                        .ease(d3.easeLinear)
                        .attr('x', (d: any) => {
                            return this.calcItemX(d, start_of_year) - (this.item_size * 1.1 - this.item_size) / 2;
                        })
                        .attr('y', (d: any) => {
                            return this.calcItemY(d) - (this.item_size * 1.1 - this.item_size) / 2;
                        })
                        .attr('width', this.item_size * 1.1)
                        .attr('height', this.item_size * 1.1)
                        .transition()
                        .duration(this.transition_duration)
                        .ease(d3.easeLinear)
                        .attr('x', (d: any) => {
                            return this.calcItemX(d, start_of_year) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
                        })
                        .attr('y', (d: any) => {
                            return this.calcItemY(d) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
                        })
                        .attr('width', (d: any) => {
                            return this.calcItemSize(d, max_value);
                        })
                        .attr('height', (d: any) => {
                            return this.calcItemSize(d, max_value);
                        })
                        .on('end', repeat);
                };
                repeat();
                let tooltip_html = '';
                tooltip_html += '<div><strong>' + (d.total ? d.total : '0') + ' 条数据创建于 ' + moment(d.date).format('dddd, MMM Do YYYY') + '</strong></div>';
                let x = this.calcItemX(d, start_of_year) + this.item_size / 2;
                if (this.width - x < (this.tooltip_width + this.tooltip_padding * 3)) {
                    x -= this.tooltip_width + this.tooltip_padding * 2;
                }
                const y = this.calcItemY(d) + this.item_size / 2;
                this.tooltip.html(tooltip_html)
                    .style('left', x + 'px')
                    .style('top', y + 'px')
                    .transition()
                    .duration(this.transition_duration / 2)
                    .ease(d3.easeLinear)
                    .style('opacity', 1);
            })
            .on('mouseout', () => {
                if (this.in_transition) {
                    return;
                }
                d3.select(d3.event.currentTarget).transition()
                    .duration(this.transition_duration / 2)
                    .ease(d3.easeLinear)
                    .attr('x', (d: any) => {
                        return this.calcItemX(d, start_of_year) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
                    })
                    .attr('y', (d: any) => {
                        return this.calcItemY(d) + (this.item_size - this.calcItemSize(d, max_value)) / 2;
                    })
                    .attr('width', (d: any) => {
                        return this.calcItemSize(d, max_value);
                    })
                    .attr('height', (d: any) => {
                        return this.calcItemSize(d, max_value);
                    });
                this.hideTooltip();
            })
            .transition()
            .delay(() => {
                return (Math.cos(Math.PI * Math.random()) + 1) * this.transition_duration;
            })
            .duration(() => {
                return this.transition_duration;
            })
            .ease(d3.easeLinear)
            .style('opacity', 1)
            .call((transition: any, callback: any) => {
                if (transition.empty()) {
                    callback();
                }
                let n = 0;
                transition
                    .each(() => ++n)
                    .on('end', function () {
                        if (!--n) {
                            callback.apply(this, arguments);
                        }
                    });
            }, () => {
                this.in_transition = false;
            });
        // 添加当前年份下所有月份
        const month_labels = d3.timeMonths(start_of_year.toDate(), end_of_year.toDate());
        const monthScale = d3.scaleLinear()
            .range([0, this.width])
            .domain([0, month_labels.length]);
        this.labels.selectAll('.label-month').remove();
        this.labels.selectAll('.label-month')
            .data(month_labels)
            .enter()
            .append('text')
            .attr('class', 'label label-month')
            .attr('font-size', () => {
                return Math.floor(this.label_padding / 3) + 'px';
            })
            .text((d: any) => {
                return d.toLocaleDateString(this.locale, { month: 'short' });
            })
            .attr('x', (d: any, i: number) => {
                return monthScale(i) + (monthScale(i) - monthScale(i - 1)) / 2;
            })
            .attr('y', this.label_padding / 2)
            .on('mouseenter', (d: any) => {
                if (this.in_transition) {
                    return;
                }
                const selected_month = moment(d);
                this.items.selectAll('.item-circle')
                    .transition()
                    .duration(this.transition_duration)
                    .ease(d3.easeLinear)
                    .style('opacity', (d: any) => {
                        return moment(d.date).isSame(selected_month, 'month') ? 1 : 0.1;
                    });
            })
            .on('mouseout', () => {
                if (this.in_transition) {
                    return;
                }
                this.items.selectAll('.item-circle')
                    .transition()
                    .duration(this.transition_duration)
                    .ease(d3.easeLinear)
                    .style('opacity', 1);
            })
        // 添加当前年份所有天数数据
        const day_labels = d3.timeDays(
            moment().startOf('week').toDate(),
            moment().endOf('week').toDate()
        );
        const dayScale = d3.scaleBand()
            .rangeRound([this.label_padding, this.height])
            .domain(day_labels.map((d: any) => {
                return moment(d).weekday().toString();
            }));
        this.labels.selectAll('.label-day').remove();
        this.labels.selectAll('.label-day')
            .data(day_labels)
            .enter()
            .append('text')
            .attr('class', 'label label-day')
            .attr('x', this.label_padding / 3)
            .attr('y', (d: any, i: number) => {
                return dayScale((i).toString()) + dayScale.bandwidth() / 1.75;
            })
            .style('text-anchor', 'left')
            .attr('font-size', () => {
                return Math.floor(this.label_padding / 3) + 'px';
            })
            .text((d: any) => {
                // 中文截取汉字
                if (this.locale === 'zh-cn') {
                    return moment(d).format('dddd')[2];
                }
                return moment(d).format('dddd')[0];
            })
            .on('mouseenter', (d: any) => {
                if (this.in_transition) {
                    return;
                }
                const selected_day = moment(d);
                this.items.selectAll('.item-circle')
                    .transition()
                    .duration(this.transition_duration)
                    .ease(d3.easeLinear)
                    .style('opacity', (d: any) => {
                        return (moment(d.date).day() === selected_day.day()) ? 1 : 0.1;
                    });
            })
            .on('mouseout', () => {
                if (this.in_transition) {
                    return;
                }
                this.items.selectAll('.item-circle')
                    .transition()
                    .duration(this.transition_duration)
                    .ease(d3.easeLinear)
                    .style('opacity', 1);
            });
    }

    calcItemX(d: any, start_of_year: any) {
        const date = moment(d.date);
        const dayIndex = Math.round((+date - +moment(start_of_year).startOf('week')) / 86400000);
        const colIndex = Math.trunc(dayIndex / 7);
        return colIndex * (this.item_size + this.gutter) + this.label_padding;
    }

    calcItemY(d: any) {
        return this.label_padding + moment(d.date).weekday() * (this.item_size + this.gutter);
    }

    calcItemSize(d: any, max: number) {
        if (max <= 0) {
            return this.item_size;
        }
        return this.item_size * 0.75 + (this.item_size * d.total / max) * 0.25;
    }

    hideTooltip() {
        this.tooltip.transition()
            .duration(this.transition_duration / 2)
            .ease(d3.easeLinear)
            .style('opacity', 0);
    }

    formatTime(seconds: number) {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds - (hours * 3600)) / 60);
        let time = '';
        if (hours > 0) {
            time += hours === 1 ? '1 hour ' : hours + ' hours ';
        }
        if (minutes > 0) {
            time += minutes === 1 ? '1 minute' : minutes + ' minutes';
        }
        if (hours === 0 && minutes === 0) {
            time = Math.round(seconds) + ' seconds';
        }
        return time;
    }

}
