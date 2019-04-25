import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

@Component({
    selector: 'jhi-bon-livraison-detail',
    templateUrl: './bon-livraison-detail.component.html'
})
export class BonLivraisonDetailComponent implements OnInit {
    bonLivraison: IBonLivraison;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bonLivraison }) => {
            this.bonLivraison = bonLivraison;
        });
    }

    previousState() {
        window.history.back();
    }
}
