import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    ApproComponent,
    ApproDetailComponent,
    ApproUpdateComponent,
    ApproDeletePopupComponent,
    ApproDeleteDialogComponent,
    approRoute,
    approPopupRoute
} from './';

const ENTITY_STATES = [...approRoute, ...approPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ApproComponent, ApproDetailComponent, ApproUpdateComponent, ApproDeleteDialogComponent, ApproDeletePopupComponent],
    entryComponents: [ApproComponent, ApproUpdateComponent, ApproDeleteDialogComponent, ApproDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaApproModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
