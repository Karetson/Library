package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.api.borrow.dto.CreateBorrowRequest;
import pl.library.api.borrow.dto.CreateBorrowResponse;
import pl.library.api.borrow.dto.GetBorrowResponse;
import pl.library.domain.borrow.BorrowServiceImpl;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BorrowServiceImpl borrowService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBorrowResponse addBorrow(@Valid @RequestBody CreateBorrowRequest createBorrowRequest) throws UserNotFoundException {
        Borrow addedBorrow = borrowService.addition(createBorrowRequest.toBorrow());
        return new CreateBorrowResponse(addedBorrow.getId());
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<GetBorrowResponse> getAllBorrowsByStatus(@RequestParam BorrowStatus status) {
        List<Borrow> gainedBorrows = borrowService.getAllBorrowsByStatus(status);
        return gainedBorrows.stream().map(GetBorrowResponse::new).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBorrowResponse changeBorrowStatus(@PathVariable Long id, @RequestParam BorrowStatus status) {
        Borrow updatedBorrow = borrowService.changeBorrowStatus(id, status);
        return new CreateBorrowResponse(updatedBorrow.getId());
    }
}