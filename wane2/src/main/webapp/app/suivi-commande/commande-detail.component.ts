import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommande } from 'app/shared/model/commande.model';


import {CommandeService} from "./commande.service";
import {IArticle} from "../shared/model/article.model";

@Component({
    selector: 'jhi-commande-detail',
    templateUrl: './commande-detail.component.html'
})
export class CommandeDetailComponent implements OnInit {
    commande: ICommande;
    article:any[]=null;
    constructor(protected activatedRoute: ActivatedRoute,
                protected commandeService: CommandeService) {}

    ngOnInit() {


        this.activatedRoute.data.subscribe(({ commande }) => {

            this.commande = commande;
            console.log(this.commande)

        });
        this.loadArticle();

    }
    loadArticle(){
        this.commandeService.findarticle(this.activatedRoute.snapshot.paramMap.get('id')).subscribe(
            (data)=>{

                this.article=data.body;

            },
            ()=>{
                console.log("error")
            })
    }

    previousState() {
        this.article=null;
        window.history.back();
    }
}
