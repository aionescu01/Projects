import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditDriverComponent } from './dialog-add-edit-car.component';

describe('DialogAddEditDriverComponent', () => {
  let component: DialogAddEditDriverComponent;
  let fixture: ComponentFixture<DialogAddEditDriverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditDriverComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditDriverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
