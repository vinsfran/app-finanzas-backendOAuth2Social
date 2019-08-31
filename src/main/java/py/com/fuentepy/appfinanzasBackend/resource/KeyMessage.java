package py.com.fuentepy.appfinanzasBackend.resource;

public interface KeyMessage {
	String MALFORMED_JSON = "MalformedJSON";
	String MALFORMED_FORM_DATA = "MalformedFormData";
	String QUERY_NOT_ALLOWED = "QueryNotAllowed";
	String INVALID_PARAMETERS = "InvalidParameters";
	String MISSING_PARAMETERS = "MissingParameter";
	String SUBSCRIBER_NOT_FOUND = "SubscriberNotFound";
	String QUERY_NOT_PROCESSED = "QueryNotProcessed"; 
	String NO_RESPONSE_FROM_HOST = "NoResponseFromHost"; 
	String HOST_TRANSACTION_ERROR = "HostTransactionError"; 
	String UNKNOWN_ERROR = "UnknownError"; 
	String SUBSCRIBER_WITHOUT_DEBT = "SubscriberWithoutDebt"; 
	String QUERY_PROCESSED = "QueryProcessed";
	String OVER_DUE_INVOICE = "OverdueInvoice"; 
	String PAYMENT_NOT_AUTHORIZED = "PaymentNotAuthorized"; 
	String PAYMENT_PROCESSED = "PaymentProcessed";
	String ALREADY_REVERSED = "AlreadyReversed";
	String SUBSCRIBER_ID_NOT_CREATED = "SubscriberIdNotCreated";
	String SUBSCRIBER_ID_CREATED = "SubscriberIdCreated";
	String TRANSACTION_NOT_REVERSED = "TransactionNotReversed";
	String TRANSACTION_REVERSED ="TransactionReversed";
	
}
