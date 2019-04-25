import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailCom } from 'app/shared/model/detail-com.model';

@Component({
    selector: 'jhi-detail-com-detail',
    templateUrl: './detail-com-detail.component.html'
})
export class DetailComDetailComponent implements OnInit {
    detailCom: IDetailCom;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detailCom }) => {
            this.detailCom = detailCom;
        });
    }

    previousState() {
        window.history.back();
    }
}
