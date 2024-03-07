import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostCarComponent } from './post-car.component';

describe('PostCarComponent', () => {
  let component: PostCarComponent;
  let fixture: ComponentFixture<PostCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostCarComponent]
    });
    fixture = TestBed.createComponent(PostCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
