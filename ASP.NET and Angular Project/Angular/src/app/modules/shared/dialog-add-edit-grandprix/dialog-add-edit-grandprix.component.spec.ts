import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditGrandprixComponent } from './dialog-add-edit-grandprix.component';

describe('DialogAddEditGrandprixComponent', () => {
  let component: DialogAddEditGrandprixComponent;
  let fixture: ComponentFixture<DialogAddEditGrandprixComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditGrandprixComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditGrandprixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
