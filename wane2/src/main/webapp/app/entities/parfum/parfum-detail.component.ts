import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParfum } from 'app/shared/model/parfum.model';

@Component({
    selector: 'jhi-parfum-detail',
    templateUrl: './parfum-detail.component.html'
})
export class ParfumDetailComponent implements OnInit {
    parfum: IParfum;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parfum }) => {
            this.parfum = parfum;
        });
    }

    previousState() {
        window.history.back();
    }
}
