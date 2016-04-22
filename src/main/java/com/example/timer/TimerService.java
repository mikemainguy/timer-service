package com.example.timer;

import javax.jws.WebService;

/**
 * Created by michaelmainguy on 2/20/16.
 */
@WebService
public interface TimerService {

    public TimerEvent addTimer(TimerEvent te);


}
