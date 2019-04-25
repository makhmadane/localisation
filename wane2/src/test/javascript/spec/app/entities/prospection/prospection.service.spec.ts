/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProspectionService } from 'app/entities/prospection/prospection.service';
import { IProspection, Prospection } from 'app/shared/model/prospection.model';

describe('Service Tests', () => {
    describe('Prospection Service', () => {
        let injector: TestBed;
        let service: ProspectionService;
        let httpMock: HttpTestingController;
        let elemDefault: IProspection;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProspectionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Prospection(0, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        datedepart: currentDate.format(DATE_FORMAT),
                        datecreation: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Prospection', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        datedepart: currentDate.format(DATE_FORMAT),
                        datecreation: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        datedepart: currentDate,
                        datecreation: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Prospection(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Prospection', async () => {
                const returnedFromService = Object.assign(
                    {
                        datedepart: currentDate.format(DATE_FORMAT),
                        datecreation: currentDate.format(DATE_FORMAT),
                        heuredepart: 'BBBBBB',
                        heurearrive: 'BBBBBB',
                        journee: 'BBBBBB',
                        nbreatteint: 'BBBBBB',
                        nbrprevue: 'BBBBBB',
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        datedepart: currentDate,
                        datecreation: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Prospection', async () => {
                const returnedFromService = Object.assign(
                    {
                        datedepart: currentDate.format(DATE_FORMAT),
                        datecreation: currentDate.format(DATE_FORMAT),
                        heuredepart: 'BBBBBB',
                        heurearrive: 'BBBBBB',
                        journee: 'BBBBBB',
                        nbreatteint: 'BBBBBB',
                        nbrprevue: 'BBBBBB',
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        datedepart: currentDate,
                        datecreation: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Prospection', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
