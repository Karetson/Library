package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import pl.library.domain.borrow.BorrowService;
import pl.library.domain.borrow.exception.BorrowStatusException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow/auth")
public class BorrowController {
    private final BorrowService borrowService;

    // creating a borrow
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBorrowResponse addBorrow(@Valid @RequestBody CreateBorrowRequest createBorrowRequest) throws UserNotFoundException {
        Borrow addedBorrow = borrowService.addBorrow(createBorrowRequest.toBorrow());
        return new CreateBorrowResponse(addedBorrow.getId());
    }

    // setting borrow status
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public CreateBorrowResponse changeBorrowStatus(@PathVariable Long id, @RequestParam BorrowStatus status) {
        Borrow updatedBorrow = borrowService.changeBorrowStatus(id, status);
        return new CreateBorrowResponse(updatedBorrow.getId());
    }

    // deleting borrow
    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBorrow(@PathVariable Long id) throws BorrowStatusException {
        borrowService.deleteBorrow(id);
    }
}
