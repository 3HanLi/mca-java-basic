package com.mca.concurrent.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtomicFieldUser {

    /**
     * 1 	必须是可访问的
     * 2	必须是volatile
     */
    volatile int id;

    private String name;

}
