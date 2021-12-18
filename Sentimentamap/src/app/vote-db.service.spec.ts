import { TestBed } from '@angular/core/testing';

import { VoteDBService } from './vote-db.service';

describe('VoteDBService', () => {
  let service: VoteDBService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoteDBService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
