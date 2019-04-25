import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    DetailComComponent,
    DetailComDetailComponent,
    DetailComUpdateComponent,
    DetailComDeletePopupComponent,
    DetailComDeleteDialogComponent,
    detailComRoute,
    detailComPopupRoute
} from './';

const ENTITY_STATES = [...detailComRoute, ...detailComPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DetailComComponent,
        DetailComDetailComponent,
        DetailComUpdateComponent,
        DetailComDeleteDialogComponent,
        DetailComDeletePopupComponent
    ],
    entryComponents: [DetailComComponent, DetailComUpdateComponent, DetailComDeleteDialogComponent, DetailComDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaDetailComModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
