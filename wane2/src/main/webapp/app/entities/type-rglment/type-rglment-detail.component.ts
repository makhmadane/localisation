import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeRglment } from 'app/shared/model/type-rglment.model';

@Component({
    selector: 'jhi-type-rglment-detail',
    templateUrl: './type-rglment-detail.component.html'
})
export class TypeRglmentDetailComponent implements OnInit {
    typeRglment: ITypeRglment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeRglment }) => {
            this.typeRglment = typeRglment;
        });
    }

    previousState() {
        window.history.back();
    }
}
