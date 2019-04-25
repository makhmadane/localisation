import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITablette } from 'app/shared/model/tablette.model';

@Component({
    selector: 'jhi-tablette-detail',
    templateUrl: './tablette-detail.component.html'
})
export class TabletteDetailComponent implements OnInit {
    tablette: ITablette;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tablette }) => {
            this.tablette = tablette;
        });
    }

    previousState() {
        window.history.back();
    }
}
