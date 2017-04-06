package osgi;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.partnamerules.PartNameTokenReplacementService;
import com.dscsag.plm.spi.interfaces.partnamerules.TokenReplacementRequest;
import com.dscsag.plm.spi.interfaces.partnamerules.TokenReplacementResponse;

/**
 * returns the value for token $(TEMPLATE_FILENAME)
 * 
 * example usage in DType:
 * <document_create
 *  filename_template_base="$(DOCNUMBER)$(DOCTYPE)$(DOCPART)$(TEMPLATE_FILENAME)_"
 *
 */
public class SimpleTokenReplacer implements PartNameTokenReplacementService
{
  private static final String TEMPLATE_FILENAME_TOKEN = "$(TEMPLATE_FILENAME)";

  private ECTRService ectrService;

  public SimpleTokenReplacer(ECTRService ectrService)
  {
    this.ectrService = ectrService;
  }

  @Override
  public TokenReplacementResponse getApplicationFileName(TokenReplacementRequest request)
  {
    ectrService.getPlmLogger()
        .debug(
            "SimpleTokenReplacer: in: {appl_type: " + request.getApplicationType() + ", doc_number: " + request.getDocumentNumber()
                + ", doc_number_long: "
                + request.getDocumentNumberInternal() + ", doc_type: " + request.getDocumentType() + ", doc_part: "
                + request.getDocumentPart() + ", doc_version: "
                + request.getDocumentVersion() + ", dtype: " + request.getDType() + "}");

    Map<String, String> replacement = new HashMap<String, String>();
    if ("UGS".equalsIgnoreCase(request.getApplicationType()))
    {
      if (request.getUnknownTokens().contains(TEMPLATE_FILENAME_TOKEN))
      {
        replacement.put(TEMPLATE_FILENAME_TOKEN, "");
        if (request.getTemplateFileName() != null)
        {
          String fname = Paths.get(request.getTemplateFileName()).getFileName().toString();
          if (!"template.prt".equalsIgnoreCase(fname))
          {
            int idxOfDot = fname.indexOf('.');
            if (idxOfDot > 0 && idxOfDot < fname.length())
              fname = fname.substring(0, idxOfDot);
            if (!"".equals(fname))
              fname = "-" + fname;
            replacement.put(TEMPLATE_FILENAME_TOKEN, fname.toLowerCase());
          }
        }
      }
    }
    return () -> replacement;
  }
}