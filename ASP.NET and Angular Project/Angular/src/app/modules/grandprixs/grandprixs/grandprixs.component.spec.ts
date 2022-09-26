import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrandprixsComponent } from './grandprixs.component';

describe('GrandprixsComponent', () => {
  let component: GrandprixsComponent;
  let fixture: ComponentFixture<GrandprixsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GrandprixsComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrandprixsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
