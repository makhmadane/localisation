import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommande } from 'app/shared/model/commande.model';
import { AccountService } from 'app/core';
import { CommandeService } from './commande.service';

@Component({
    selector: 'jhi-commande',
    templateUrl: './commande.component.html'
})
export class CommandeComponent implements OnInit, OnDestroy {
    x:ICommande;
    motChangeEtat:any="dane";
    motIdent:any;
    commandes: ICommande[];
    commandes1: ICommande[];
    commandes2: ICommande[];
    motetat: any="";
    motboutique: any="";
    motSecteur: any="";
    motprevendeur: any="";
    etat: any[];
    boutique: any[];
    date: any[];
    prevendeur: any[];

    etat: any[];
    secteur: any[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    nom2="wwww"
    nom1="dane"
    constructor(
        protected commandeService: CommandeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }
    ChangeEtat(){
      this.commandeService.changeEtat(this.motChangeEtat,this.motIdent).subscribe(
          ()=>{
              console.log('succes');
              this.replaceValeurTable();
          },
          ()=>{
              console.log('failed');
          }
      )
    }
    replaceValeurTable(){
    for (let entry of this.commandes) {
        if(entry.id == this.motIdent){
            entry.etat = this.motChangeEtat;
        }

    }
        for (let entry of this.commandes1) {
            if(entry.id == this.motIdent){
                entry.etat = this.motChangeEtat;
            }

        }
    }

    findAlletat(){
        this.commandeService.findAllEtat().subscribe(
        (data)=>{

            this.etat=data.body;
        }
        );

    }

    findAllBoutique(){

        this.commandeService.findAllBoutique().subscribe(
            (data)=>{
                this.boutique=data.body;
            }
        );

    }
    findAllSecteur(){

        this.commandeService.findAllSecteur().subscribe(
            (data)=>{
                this.secteur=data.body;
            }
        );

    }
    findAllPrevendeur(){
        this.commandeService.findAllPrevendeur().subscribe(
            (data)=>{
                this.prevendeur=data.body;
            }
        );

    }
   /* findAllDate(){
        this.commandeService.findAllDate().subscribe(
            (data)=>{
                this.secteur=data.body;
            }
        );

    }*/

   filtreEtat(){

       this.commandes=this.commandes1;
       if(this.motetat!=0 ){
           this.commandes2=this.commandes.filter(a=>a.etat == this.motetat)
           this.commandes=this.commandes2;
       }

       if(this.motboutique!=0 ){
           this.commandes2=this.commandes.filter(a=>a.boutique.nomBoutique==this.motboutique )
           this.commandes=this.commandes2;
       }
       if(this.motSecteur!=0 ){
           console.log("dane")
           this.commandes2=this.commandes.filter(a=>a.secteur.nomSecteur==this.motSecteur )
           this.commandes=this.commandes2;
       }



    }
filtreRoute(name){
    this.commandes=this.commandes.filter(a=>a.detailRoute.route.numero==name )
}

filtreSecteur(){
    this.commandes=this.commandes1;
    this.commandes=this.commandes.filter(a=>a.boutique.secteur.nomSecteur==this.motSecteur )
    this.commandes1=this.commandes;
}
filtreDate(name){
    this.commandes=this.commandes.filter(a=>a.dateCom==name )

}
filtrePrevendeur(){
    this.commandes=this.commandes1;
    this.commandes=this.commandes.filter(a=>a.prevendeur.nomcomplet==this.motprevendeur )
    this.commandes1=this.commandes;
}

    loadAll() {
        if (this.currentSearch) {
            this.commandeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ICommande[]>) => res.ok),
                    map((res: HttpResponse<ICommande[]>) => res.body)
                )
                .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.commandeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICommande[]>) => res.ok),
                map((res: HttpResponse<ICommande[]>) => res.body)
            )
            .subscribe(
                (res: ICommande[]) => {

                    this.commandes = res;
                    this.commandes1 = res;
                    this.commandes2 = res;
                    this.currentSearch = '';


                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    close(){
      // this.commandes=null;
        this.loadAll();
        //this.loadAll();
      //  this.filtreEtat();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();

    }

    ngOnInit() {
        this.loadAll();
        this.findAlletat();
        this.findAllSecteur();
        this.findAllBoutique();
        this.findAllPrevendeur();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCommandes();

    }
    popUp(id,name){
        this.motIdent=id;
        this.motChangeEtat=name
        document.getElementById('etat').click();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommande) {
        return item.id;
    }

    registerChangeInCommandes() {
        this.eventSubscriber = this.eventManager.subscribe('commandeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
