import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';

@Component({
    selector: 'jhi-moyen-transport-detail',
    templateUrl: './moyen-transport-detail.component.html'
})
export class MoyenTransportDetailComponent implements OnInit {
    moyenTransport: IMoyenTransport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moyenTransport }) => {
            this.moyenTransport = moyenTransport;
        });
    }

    previousState() {
        window.history.back();
    }
}
