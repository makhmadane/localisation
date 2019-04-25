import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeRoute } from 'app/shared/model/type-route.model';

@Component({
    selector: 'jhi-type-route-detail',
    templateUrl: './type-route-detail.component.html'
})
export class TypeRouteDetailComponent implements OnInit {
    typeRoute: ITypeRoute;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeRoute }) => {
            this.typeRoute = typeRoute;
        });
    }

    previousState() {
        window.history.back();
    }
}
