import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SponsorsService } from '../../../services/sponsors.service';

@Component({
  selector: 'app-dialog-add-edit-sponsor',
  templateUrl: './dialog-add-edit-sponsor.component.html',
  styleUrls: ['./dialog-add-edit-sponsor.component.scss']
})
export class DialogAddEditSponsorComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public sponsorsForm: FormGroup = new FormGroup({
    id: new FormControl(),
    name: new FormControl(''),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditSponsorComponent>,
    private sponsorsService: SponsorsService,
  ) {
    console.log(this.data);
    if (this.data.sponsor) {
      this.title = 'Edit Sponsor';
      this.sponsorsForm.patchValue(this.data.sponsor);
      this.isEditable = true;
    } else {
      this.title = 'Add Sponsor';
      this.isEditable = false;
    }
  }

  get id(): AbstractControl {
    return this.sponsorsForm.get('id');
  }
  get name(): AbstractControl {
    return this.sponsorsForm.get('name');
  }


  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.sponsorsForm.value);
    if (this.isEditable === false) {
      this.sponsorsService.createSponsor(this.sponsorsForm.value).subscribe(() => {
        this.dialogRef.close(this.sponsorsForm.value);
      });
    } else {
      this.sponsorsService.editSponsor(this.sponsorsForm.value).subscribe(() => {
        this.dialogRef.close(this.sponsorsForm.value);
      });
    }
  }

}
