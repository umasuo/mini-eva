package cn.eva.mini.infra.exception.handler;

import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.AuthFailedException;
import cn.eva.mini.infra.exception.AuthInfoMissingException;
import cn.eva.mini.infra.exception.ConflictException;
import cn.eva.mini.infra.exception.CreateResourceFailed;
import cn.eva.mini.infra.exception.ImmutableException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.exception.ParametersException;
import cn.eva.mini.infra.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler {

  /**
   * logger.
   */
  Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

  /**
   * exception EXCEPTION_MAP.
   */
  Map<Class<?>, HttpStatus> EXCEPTION_MAP = new ConcurrentHashMap<Class<?>, HttpStatus>() {
    {
      this.put(AlreadyExistException.class, HttpStatus.CONFLICT);
      this.put(AuthFailedException.class, HttpStatus.UNAUTHORIZED);
      this.put(AuthInfoMissingException.class, HttpStatus.BAD_REQUEST);
      this.put(ConflictException.class, HttpStatus.CONFLICT);
      this.put(CreateResourceFailed.class, HttpStatus.INTERNAL_SERVER_ERROR);
      this.put(ImmutableException.class, HttpStatus.FORBIDDEN);
      this.put(NotExistException.class, HttpStatus.NOT_FOUND);
      this.put(ParametersException.class, HttpStatus.BAD_REQUEST);
      this.put(PasswordErrorException.class, HttpStatus.UNAUTHORIZED);
    }
  };

  /**
   * exception that do not log.
   */
  List<Class<?>> OMITTED_EXCEPTIONS = Arrays.asList(
      AlreadyExistException.class,
      AuthFailedException.class,
      AuthInfoMissingException.class,
      ConflictException.class,
      CreateResourceFailed.class,
      ImmutableException.class,
      NotExistException.class,
      ParametersException.class,
      PasswordErrorException.class
  );


  /**
   * set the error info that need to send back to the client.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @param obj      Object
   * @param ex       Exception
   * @return the input exception
   */
  public Exception setResponse(HttpServletRequest request, HttpServletResponse response,
                                Object obj,
                                Exception ex) {
    // get the status
    HttpStatus status = EXCEPTION_MAP.get(ex.getClass());
    if (status == null) {
      //if this is an unexpected exception, set the code to internal server error.
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    logException(request, response, obj, status, ex);

    response.setStatus(status.value());
    response.setHeader("ErrorMessage", ex.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON.getType());
    return ex;
  }

  /**
   * log the detail of the exception.
   * only log these exceptions that does not excepted.
   *
   * @param ex exception
   */
  public void logException(HttpServletRequest request, HttpServletResponse response,
                            Object obj, HttpStatus status, Exception ex) {
    boolean shouldLog = !OMITTED_EXCEPTIONS.contains(ex.getClass());
    if (shouldLog) {
      // only log those ones that are real failures
      LOG.error("request {}, response {}, obj {}, status {}", request, response, obj, status, ex);
    }
  }
}
