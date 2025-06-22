export interface ReservationDTO{
    reservationId:number;
	startDate:string;
	endDate:string;
	customerId:number; 
	carId:number;
	reservationStatus:string;
	checkInTime:string;
	checkOutTime:string;
	customerName?: string;
}     