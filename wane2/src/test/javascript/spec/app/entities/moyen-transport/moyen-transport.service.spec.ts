/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MoyenTransportService } from 'app/entities/moyen-transport/moyen-transport.service';
import { IMoyenTransport, MoyenTransport } from 'app/shared/model/moyen-transport.model';

describe('Service Tests', () => {
    describe('MoyenTransport Service', () => {
        let injector: TestBed;
        let service: MoyenTransportService;
        let httpMock: HttpTestingController;
        let elemDefault: IMoyenTransport;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MoyenTransportService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new MoyenTransport(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateList: currentDate.format(DATE_FORMAT)
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

            it('should create a MoyenTransport', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateList: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateList: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new MoyenTransport(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a MoyenTransport', async () => {
                const returnedFromService = Object.assign(
                    {
                        matricule: 'BBBBBB',
                        dateList: currentDate.format(DATE_FORMAT),
                        etat: 'BBBBBB',
                        vitesse: 'BBBBBB',
                        capacite: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateList: currentDate
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

            it('should return a list of MoyenTransport', async () => {
                const returnedFromService = Object.assign(
                    {
                        matricule: 'BBBBBB',
                        dateList: currentDate.format(DATE_FORMAT),
                        etat: 'BBBBBB',
                        vitesse: 'BBBBBB',
                        capacite: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateList: currentDate
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

            it('should delete a MoyenTransport', async () => {
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
