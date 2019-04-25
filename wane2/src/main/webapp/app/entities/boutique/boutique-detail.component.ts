import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoutique } from 'app/shared/model/boutique.model';

@Component({
    selector: 'jhi-boutique-detail',
    templateUrl: './boutique-detail.component.html'
})
export class BoutiqueDetailComponent implements OnInit {
    boutique: IBoutique;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ boutique }) => {
            this.boutique = boutique;
        });
    }

    previousState() {
        window.history.back();
    }
}
