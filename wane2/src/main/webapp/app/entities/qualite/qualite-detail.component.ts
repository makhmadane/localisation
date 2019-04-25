import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQualite } from 'app/shared/model/qualite.model';

@Component({
    selector: 'jhi-qualite-detail',
    templateUrl: './qualite-detail.component.html'
})
export class QualiteDetailComponent implements OnInit {
    qualite: IQualite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ qualite }) => {
            this.qualite = qualite;
        });
    }

    previousState() {
        window.history.back();
    }
}
