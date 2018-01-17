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
export class ChartsUtils {

    public static gerenateLineChart(toolbox: boolean, type: string, xData: any[], seriesData: any[]) {
        const lineChart = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            toolbox: {
                show: toolbox,
                feature: {
                    magicType: {
                        show: true,
                        type: ['bar', 'line']
                    },
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                show: false,
                type: 'category',
                data: xData,
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    interval: 0,
                    rotate: 20
                },
            }],
            yAxis: [{
                type: 'value'
            }],
            series: [{
                type: type,
                barWidth: '60%',
                label: {
                    normal: {
                        show: true
                    }
                },
                data: seriesData
            }]
        };
        return lineChart;
    }

    /**
     * 生成字符云图表
     * @param tooltip 是否显示提示信息
     * @param wordCloudData 字符云数据
     */
    public static gerenateWordCloudChart(tooltip: boolean, wordCloudData: any[]) {
        const wordCloudChart = {
            tooltip: {
                show: tooltip
            },
            series: [{
                type: 'wordCloud',
                size: ['80%', '80%'],
                textRotation: [0, 45, 90, -45],
                textPadding: 0,
                autoSize: {
                    enable: true,
                    minSize: 14
                },
                data: wordCloudData
            }]
        };
        return wordCloudChart;
    }

}
