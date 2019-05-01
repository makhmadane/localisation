import './vendor.ts';

import {MatCardModule} from '@angular/material/card';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { YesColaSharedModule } from 'app/shared';
import { YesColaCoreModule } from 'app/core';
import { YesColaAppRoutingModule } from './app-routing.module';
import { YesColaHomeModule } from './home/home.module';
import { YesColaAccountModule } from './account/account.module';
import { YesColaEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import {CommandeModule} from "./livraison/livraison.module";

import { DragDropModule } from '@angular/cdk/drag-drop';
import {DragAndDropModule} from "./drag-and-drop/drag-and-drop.module";
import {jhiCdkDragDropConnectedSortingGroupExampleComponent} from "./drag-and-drop/drag-and-drop.component";
import {Suivi_Com_Module} from "./suivi-commande/commande.module";
import {Suivi_Liv_Module} from "./suivi-livraison/commande.module";




@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'fr'
        }),
        YesColaSharedModule.forRoot(),
        YesColaCoreModule,
        YesColaHomeModule,
        YesColaAccountModule,
        MatCardModule,
        Suivi_Com_Module,
        CommandeModule,
        DragDropModule,
        DragAndDropModule,
        Suivi_Liv_Module,



        // jhipster-needle-angular-add-module JHipster will add new module here
        YesColaEntityModule,
        YesColaAppRoutingModule,

    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent,jhiCdkDragDropConnectedSortingGroupExampleComponent]
})
export class YesColaAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
