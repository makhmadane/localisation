import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    RouteComponent,
    RouteDetailComponent,
    RouteUpdateComponent,
    RouteDeletePopupComponent,
    RouteDeleteDialogComponent,
    routeRoute,
    routePopupRoute
} from './';

const ENTITY_STATES = [...routeRoute, ...routePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RouteComponent, RouteDetailComponent, RouteUpdateComponent, RouteDeleteDialogComponent, RouteDeletePopupComponent],
    entryComponents: [RouteComponent, RouteUpdateComponent, RouteDeleteDialogComponent, RouteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaRouteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
