import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map, catchError, tap } from "rxjs/operators";
import { Observable, of } from "rxjs";
import { ServerJSON } from "./models/Server"


@Injectable({
  providedIn: 'root'
})

export class ComputeService {
  instance_url = "http://localhost:8008";
  compute_url = `${this.instance_url}/api/v1/instance`;

  httpOptions = {
      headers:
        new HttpHeaders({
          'Content-Type': 'application/json'
        })
  };

  constructor(private httpClient: HttpClient) {
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.log(`${operation} failed: ${error.message}`);
      try {
        return JSON.parse(error)
      } catch (error) {
        console.error("Response doesn't have JSON format");
        return of(result as T);
      }
   };
  }

  getServers(): Observable<Array<ServerJSON>> {
    const url = `${this.compute_url}/all_stub`;
    return this
      .httpClient
      .get<Array<ServerJSON>>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => console.log('Fetching servers')),
        catchError(this.handleError<Array<ServerJSON>>('getServers', []))
      );
  }

  startServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/start`;
    console.log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
      tap(_ =>
        console.log(
          `Perform start action, server id: ${serverId}`
        )
      ),
      catchError(this.handleError<any>('start action'))
      ).subscribe();
  }

  stopServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/stop`;
    console.log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post<any>(url, "")
      .pipe(
        tap(_ =>
          console.log(
            `Perform stop action, server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('stop action'))
      ).subscribe();
  }

  softRebootServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/softreboot`;
    console.log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          console.log(
            `Perform soft reboot action server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('soft reboot action'))
      ).subscribe();
  }

  hardRebootServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/hardreboot`;
    console.log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          console.log(
            `Perform hard reboot action server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('hard reboot action'))
      ).subscribe();
  }

  deleteServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/delete`;
    console.log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .delete(url)
      .pipe(
        tap(_ =>
          console.log(
            `Perform delete action server id: ${serverId}`
          )
        )
      ).subscribe();
  }

}
