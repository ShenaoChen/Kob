package com.kob.matchingsystem.service.impl.utills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    public Integer userId;
    public Integer rating;
    public Integer botId;
    public Integer waitingTime;
}
