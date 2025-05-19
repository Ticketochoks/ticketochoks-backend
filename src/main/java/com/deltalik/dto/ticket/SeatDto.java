    package com.deltalik.dto.ticket;

    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    @Data
    public class SeatDto {

        @NotNull(message = "The row number is required")
        @Min(1)
        private Integer rowNumber;

        @NotNull(message = "The seat number is required")
        @Min(1)
        private Integer seatNumber;
    }
