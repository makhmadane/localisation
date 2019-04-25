import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailRoute } from 'app/shared/model/detail-route.model';

@Component({
    selector: 'jhi-detail-route-detail',
    templateUrl: './detail-route-detail.component.html'
})
export class DetailRouteDetailComponent implements OnInit {
    detailRoute: IDetailRoute;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detailRoute }) => {
            this.detailRoute = detailRoute;
        });
    }

    previousState() {
        window.history.back();
    }
}
