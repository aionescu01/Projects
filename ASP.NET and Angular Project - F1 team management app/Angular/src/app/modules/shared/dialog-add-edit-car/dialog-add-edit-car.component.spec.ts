import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditCarComponent } from './dialog-add-edit-car.component';

describe('DialogAddEditCarComponent', () => {
  let component: DialogAddEditCarComponent;
  let fixture: ComponentFixture<DialogAddEditCarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditCarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
