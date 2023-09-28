import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { GrandprixsService } from '../../../services/grandprixs.service';

@Component({
  selector: 'app-dialog-add-edit-grandprix',
  templateUrl: './dialog-add-edit-grandprix.component.html',
  styleUrls: ['./dialog-add-edit-grandprix.component.scss']
})
export class DialogAddEditGrandprixComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public grandprixsForm: FormGroup = new FormGroup({
    id: new FormControl(),
    name: new FormControl(''),
    location: new FormControl(''),
    country: new FormControl(''),
    date: new FormControl(new Date('2021-12-16T10:43:46.737Z')),
    laps: new FormControl(),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditGrandprixComponent>,
    private grandprixsService: GrandprixsService,
  ) {
    console.log(this.data);
    if (this.data.grandprix) {
      this.title = 'Edit Grandprix';
      this.grandprixsForm.patchValue(this.data.grandprix);
      this.isEditable = true;
    } else {
      this.title = 'Add Grandprix';
      this.isEditable = false;
    }
  }

  get id(): AbstractControl {
    return this.grandprixsForm.get('id');
  }
  get name(): AbstractControl {
    return this.grandprixsForm.get('engineNr');
  }
  get location(): AbstractControl {
    return this.grandprixsForm.get('location');
  }
  get country(): AbstractControl {
    return this.grandprixsForm.get('country');
  }
  get date(): AbstractControl {
    return this.grandprixsForm.get('date');
  }
  get laps(): AbstractControl {
    return this.grandprixsForm.get('laps');
  }

  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.grandprixsForm.value);
    if (this.isEditable === false) {
      this.grandprixsService.createGrandprix(this.grandprixsForm.value).subscribe(() => {
        this.dialogRef.close(this.grandprixsForm.value);
      });
    } else {
      this.grandprixsService.editGrandprix(this.grandprixsForm.value).subscribe(() => {
        this.dialogRef.close(this.grandprixsForm.value);
      });
    }
  }

}
