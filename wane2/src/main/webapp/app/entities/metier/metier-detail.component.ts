import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMetier } from 'app/shared/model/metier.model';

@Component({
    selector: 'jhi-metier-detail',
    templateUrl: './metier-detail.component.html'
})
export class MetierDetailComponent implements OnInit {
    metier: IMetier;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ metier }) => {
            this.metier = metier;
        });
    }

    previousState() {
        window.history.back();
    }
}
