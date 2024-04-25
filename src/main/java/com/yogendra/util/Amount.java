package com.yogendra.util;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
public class Amount {

    private BigDecimal value;

    public Amount(String value) {
        this.value = new BigDecimal(value);
    }

    public static Amount valueOf(String amount){
        if(amount == null || amount.isEmpty()){
            throw new IllegalArgumentException("Invalid amount");
        }
        if(amount.startsWith("₹")){
            amount = amount.substring(1);
        }
      return new Amount(amount);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Amount add(Amount amount){
        return new Amount(value.add(amount.value).toString());
    }

    public Amount subtract(Amount amount){
        return new Amount(value.subtract(amount.value).toString());
    }

    @Override
    public String toString(){
        return "₹" + value.toString();
    }

    public int asInteger(){
        return value.intValue();
    }

}
