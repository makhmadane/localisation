import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeTransport } from 'app/shared/model/type-transport.model';

@Component({
    selector: 'jhi-type-transport-detail',
    templateUrl: './type-transport-detail.component.html'
})
export class TypeTransportDetailComponent implements OnInit {
    typeTransport: ITypeTransport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeTransport }) => {
            this.typeTransport = typeTransport;
        });
    }

    previousState() {
        window.history.back();
    }
}
