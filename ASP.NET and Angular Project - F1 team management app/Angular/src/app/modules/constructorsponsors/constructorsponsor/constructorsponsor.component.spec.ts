import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConstructorsponsorComponent } from './constructorsponsor.component';

describe('ConstructorsponsorComponent', () => {
  let component: ConstructorsponsorComponent;
  let fixture: ComponentFixture<ConstructorsponsorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConstructorsponsorComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConstructorsponsorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
