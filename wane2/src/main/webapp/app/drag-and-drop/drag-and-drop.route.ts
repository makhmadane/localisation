import { Route } from '@angular/router';


import {jhiCdkDragDropConnectedSortingGroupExampleComponent} from "./drag-and-drop.component";

export const DRAG_ROUTE: Route = {
    path: 'drag',
    component: jhiCdkDragDropConnectedSortingGroupExampleComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
