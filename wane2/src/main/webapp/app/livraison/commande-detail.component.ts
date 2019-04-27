import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommande } from 'app/shared/model/commande.model';

@Component({
    selector: 'jhi-commande-detail',
    templateUrl: './commande-detail.component.html'
})
export class CommandeDetailComponent implements OnInit {
    commande: ICommande;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commande }) => {
            this.commande = commande;
        });
    }

    previousState() {
        window.history.back();
    }
}
