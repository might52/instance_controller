import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map, catchError, tap } from "rxjs/operators";
import { Observable, of } from "rxjs";
import { ServerJSON } from "./models/Server"
import {log} from "util";


@Injectable({
  providedIn: 'root'
})

export class ComputeService {
  instance_url = "http://localhost:8008";
  compute_url = `${this.instance_url}/api/v1/instance`;
  get_all_url = "/all";

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
      log(`${operation} failed: ${error.message}`);
      try {
        return JSON.parse(error)
      } catch (error) {
        error("Response doesn't have JSON format");
        return of(result as T);
      }
   };
  }

  getServers(): Observable<Array<ServerJSON>> {
    const url = `${this.compute_url}${this.get_all_url}`;
    return this
      .httpClient
      .get<Array<ServerJSON>>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => log('Fetching servers')),
        catchError(this.handleError<Array<ServerJSON>>('getServers', []))
      );
  }

  startServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/start`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
      tap(_ =>
        log(
          `Perform start action, server id: ${serverId}`
        )
      ),
      catchError(this.handleError<any>('start action'))
      ).subscribe();
  }

  stopServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/stop`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post<any>(url, "")
      .pipe(
        tap(_ =>
          log(
            `Perform stop action, server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('stop action'))
      ).subscribe();
  }

  pauseServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/pause`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post<any>(url, "")
      .pipe(
        tap(_ =>
          log(
            `Perform pause action, server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('pause action'))
      ).subscribe();
  }

  unPauseServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/unpause`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post<any>(url, "")
      .pipe(
        tap(_ =>
          log(
            `Perform unpause action, server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('unpause action'))
      ).subscribe();
  }

  softRebootServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/softreboot`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Perform soft reboot action server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('soft reboot action'))
      ).subscribe();
  }

  hardRebootServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/hardreboot`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, null)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Perform hard reboot action server id: ${serverId}`
          )
        ),
        catchError(this.handleError<any>('hard reboot action'))
      ).subscribe();
  }

  deleteServer(serverId): void {
    const url = `${this.compute_url}/${serverId}/delete`;
    log(`ServerID ${serverId} and url: ${url}`);
    this
      .httpClient
      .delete(url)
      .pipe(
        tap(_ =>
          log(
            `Perform delete action server id: ${serverId}`
          )
        )
      ).subscribe();
  }

}
