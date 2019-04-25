import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoute } from 'app/shared/model/route.model';

@Component({
    selector: 'jhi-route-detail',
    templateUrl: './route-detail.component.html'
})
export class RouteDetailComponent implements OnInit {
    route: IRoute;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ route }) => {
            this.route = route;
        });
    }

    previousState() {
        window.history.back();
    }
}
