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
import { Component, OnInit, Output, Input, EventEmitter, Directive } from '@angular/core';
import { AfterViewInit, OnChanges, ViewChild } from '@angular/core';

declare var jQuery: any;
declare var editormd: any;

@Component({
    selector: 'wikift-editor',
    templateUrl: './wikift-editor.component.html'
})
export class WikiftEditorComponent implements OnInit {

    public editor: any;

    // 编辑器ID
    @Input('id')
    id: string;

    // 编辑器TOCid
    @Input('tocId')
    tocId = 'wikift-toc-container';

    // 传递的markdown数据
    @Input('markdown')
    markdown: string;

    // 是否预览模式
    @Input('preview')
    preview: Boolean = false;

    constructor() { }

    // 初始化组件
    ngAfterViewInit() {
        if (this.preview) {
            editormd.markdownToHTML(this.id, {
                markdown: this.markdown,
                tocContainer: this.tocId,
                tex: true,
                flowChart: true,
                emoji: true,
                taskList: true,
                sequenceDiagram: true
            });
        } else {
            this.editor = editormd({
                id: this.id,
                markdown: this.markdown,
                width: '100%',
                height: 490,
                syncScrolling: 'single',
                path: '../../../../assets/js/wikift-editor/lib/',
                imageUploadURL: 'api/upload/mdupload?test=dfdf'
            });
        }
    }

    /**
     * 初始化组件信息
     */
    ngOnChanges() {
    }

    ngOnInit() {
        jQuery('.editormd-preview-close-btn').hide();
    }

    /**
     * 将子组件获取的内容传输到父组件
     */
    // tslint:disable-next-line:member-ordering
    @Output()
    getEditorValue = new EventEmitter<any>();
    getValue() {
        this.getEditorValue.emit(this.editor.getMarkdown());
    }

}
