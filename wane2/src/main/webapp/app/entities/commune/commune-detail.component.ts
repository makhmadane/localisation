import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommune } from 'app/shared/model/commune.model';

@Component({
    selector: 'jhi-commune-detail',
    templateUrl: './commune-detail.component.html'
})
export class CommuneDetailComponent implements OnInit {
    commune: ICommune;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commune }) => {
            this.commune = commune;
        });
    }

    previousState() {
        window.history.back();
    }
}
