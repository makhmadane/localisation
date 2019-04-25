import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    MetierComponent,
    MetierDetailComponent,
    MetierUpdateComponent,
    MetierDeletePopupComponent,
    MetierDeleteDialogComponent,
    metierRoute,
    metierPopupRoute
} from './';

const ENTITY_STATES = [...metierRoute, ...metierPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MetierComponent, MetierDetailComponent, MetierUpdateComponent, MetierDeleteDialogComponent, MetierDeletePopupComponent],
    entryComponents: [MetierComponent, MetierUpdateComponent, MetierDeleteDialogComponent, MetierDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaMetierModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
