import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProspection } from 'app/shared/model/prospection.model';

@Component({
    selector: 'jhi-prospection-detail',
    templateUrl: './prospection-detail.component.html'
})
export class ProspectionDetailComponent implements OnInit {
    prospection: IProspection;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prospection }) => {
            this.prospection = prospection;
        });
    }

    previousState() {
        window.history.back();
    }
}
