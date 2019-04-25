import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepot } from 'app/shared/model/depot.model';

@Component({
    selector: 'jhi-depot-detail',
    templateUrl: './depot-detail.component.html'
})
export class DepotDetailComponent implements OnInit {
    depot: IDepot;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depot }) => {
            this.depot = depot;
        });
    }

    previousState() {
        window.history.back();
    }
}
