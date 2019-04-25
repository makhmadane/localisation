import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppro } from 'app/shared/model/appro.model';

@Component({
    selector: 'jhi-appro-detail',
    templateUrl: './appro-detail.component.html'
})
export class ApproDetailComponent implements OnInit {
    appro: IAppro;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appro }) => {
            this.appro = appro;
        });
    }

    previousState() {
        window.history.back();
    }
}
