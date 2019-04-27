import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YesColaSharedModule } from 'app/shared';

import {DRAG_ROUTE} from "./drag-and-drop.route";
import {jhiCdkDragDropConnectedSortingGroupExampleComponent} from "./drag-and-drop.component";


@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild([DRAG_ROUTE],
       )],
    declarations: [jhiCdkDragDropConnectedSortingGroupExampleComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DragAndDropModule {}
