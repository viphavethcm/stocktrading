import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { WatchlistService } from '../../watchlist.service';

@Component({
  selector: 'app-add-wl',
  templateUrl: './add-wl.component.html',
  styleUrls: ['./add-wl.component.css']
})
export class AddWlComponent implements OnInit {
  newName: string = "";
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<AddWlComponent>,
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
  }

  cancel(): void{
    this.dialogRef.close();
  }

  save(): void{
    this.watchlistService.createAWatchlist("U_004", this.newName).subscribe((wl) => {
      // send new array of watchlists to my-watchlist component
      this.data.watchlists.push(wl);
      this.watchlistService.sendWatchlists(this.data.watchlists);

      // send new watchlist object to edit dialog
      const newWatchlist = Object.assign({
        watchlist: wl.name,
        editable: false
      });
      this.dialogRef.close({data: newWatchlist});

    }, (err) => {
      console.log(err);
    });
  }

}
