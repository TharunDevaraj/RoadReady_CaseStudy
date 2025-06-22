export interface FeedbackDTO
{
    feedbackId:number;
    reservationId:number;
    customerId:number;
    comment:string;
    rating:number;
    adminResponse:string;
    status:string;
}