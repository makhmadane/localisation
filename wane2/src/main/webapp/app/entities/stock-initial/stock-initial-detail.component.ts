import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInitial } from 'app/shared/model/stock-initial.model';

@Component({
    selector: 'jhi-stock-initial-detail',
    templateUrl: './stock-initial-detail.component.html'
})
export class StockInitialDetailComponent implements OnInit {
    stockInitial: IStockInitial;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInitial }) => {
            this.stockInitial = stockInitial;
        });
    }

    previousState() {
        window.history.back();
    }
}
